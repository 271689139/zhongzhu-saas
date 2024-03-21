package com.zhongzhu.framework.aop;

import cn.hutool.core.io.FileUtil;
import com.zhongzhu.framework.annotations.FileCheck;
import com.zhongzhu.framework.enums.FileType;
import com.zhongzhu.utils.enums.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件校验切面
 *
 * @author admin
 */
@Component
@Slf4j
@Aspect
@ConditionalOnProperty(prefix = "file-check", name = "enabled", havingValue = "true")
public class FileCheckAspect {

    private static final long ONE_KB = 1 << 10;
    private static final long ONE_MB = ONE_KB << 10;

    @Before("@annotation(annotation)")
    public void before(JoinPoint joinPoint, FileCheck annotation) {

        final String[] suffixes = annotation.supportedSuffixes();
        final FileCheck.CheckType type = annotation.type();
        final FileType[] fileTypes = annotation.supportedFileTypes();
        final String typeCheckErrorMsg = annotation.typeCheckErrorMsg();
        final String sizeCheckErrorMsg = annotation.sizeCheckErrorMsg();
        final long maxSize = annotation.maxSize();
        final FileCheck.SizeUnit sizeUnit = annotation.sizeUnit();

        if (ArrayUtils.isEmpty(suffixes) && ArrayUtils.isEmpty(fileTypes)) {
            return;
        }

        Object[] args = joinPoint.getArgs();

        Set<String> suffixSet = new HashSet<>(Arrays.asList(suffixes));
        for (FileType fileType : fileTypes) {
            suffixSet.add(fileType.getSuffix());
        }
        Set<FileType> fileTypeSet = new HashSet<>(Arrays.asList(fileTypes));
        for (String suffix : suffixes) {
            fileTypeSet.add(FileType.getBySuffix(suffix));
        }

        for (Object arg : args) {
            if (arg instanceof MultipartFile) {
                doTypeCheck((MultipartFile) arg, type, suffixSet, fileTypeSet, typeCheckErrorMsg);
                doSizeCheck((MultipartFile) arg, maxSize, sizeUnit, sizeCheckErrorMsg);
            } else if (arg instanceof MultipartFile[]) {

                for (MultipartFile file : (MultipartFile[]) arg) {
                    doTypeCheck(file, type, suffixSet, fileTypeSet, typeCheckErrorMsg);
                    doSizeCheck(file, maxSize, sizeUnit, sizeCheckErrorMsg);
                }
            }
        }
    }

    private void doSizeCheck(MultipartFile file, long maxSize, FileCheck.SizeUnit sizeUnit, String sizeCheckErrorMsg) {
        if (maxSize < 0) {
            return;
        }
        long fileSize = file.getSize();
        long equivalentSize = maxSize;
        if (sizeUnit == FileCheck.SizeUnit.MB) {
            equivalentSize = equivalentSize * ONE_MB;
        } else if (sizeUnit == FileCheck.SizeUnit.KB) {
            equivalentSize = equivalentSize * ONE_MB;
        }
        if (fileSize > equivalentSize) {
            throw new BusinessException(sizeCheckErrorMsg);
        }
    }

    private void doTypeCheck(MultipartFile file, FileCheck.CheckType type, Set<String> suffixSet, Set<FileType> fileTypeSet, String message) {
        if (type == FileCheck.CheckType.SUFFIX) {
            doCheckSuffix(file, suffixSet, message);
        } else {
            doCheckMagicNumber(file, fileTypeSet, message);
        }
    }

    private void doCheckMagicNumber(MultipartFile file, Set<FileType> fileTypeSet, String message) {
        String magicNumber = readMagicNumber(file);
        for (FileType fileType : fileTypeSet) {
            //不准确,没想到更好的方法
            if (magicNumber.startsWith(fileType.getMagicNumber()) || fileType.getMagicNumber().startsWith(magicNumber)) {
                return;
            }
        }
        throw new BusinessException(message);
    }

    private void doCheckSuffix(MultipartFile file, Set<String> suffixSet, String message) {
        String fileName = file.getOriginalFilename();
        String fileSuffix = FileUtil.extName(fileName);
        for (String suffix : suffixSet) {
            if (suffix.toUpperCase().equalsIgnoreCase(fileSuffix)) {
                return;
            }
        }
        throw new BusinessException(message);
    }

    private String readMagicNumber(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            byte[] fileHeader = new byte[4];
            int count = is.read(fileHeader);
            if (count == -1) {
                throw new BusinessException("无效文件!");
            }
            return byteArray2Hex(fileHeader);
        } catch (IOException e) {
            throw new BusinessException("读取文件失败!");
        }
    }

    private String byteArray2Hex(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ArrayUtils.isEmpty(data)) {
            return null;
        }
        for (byte datum : data) {
            int v = datum & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        String result = stringBuilder.toString();
        log.debug("文件头: {}", result);
        return result;
    }
}
