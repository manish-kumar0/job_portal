package com.manish.jobportal.validator;

import com.manish.jobportal.annotation.CertificateLinkOrDocumentsRequired;
import com.manish.jobportal.dto.CertificateDTO;
import com.manish.jobportal.dto.DocumentsDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CertificateLinkOrDocumentsValidator implements ConstraintValidator<CertificateLinkOrDocumentsRequired, CertificateDTO> {

    @Override
    public boolean isValid(CertificateDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true; // null handled by other validations

        boolean hasLink = dto.getCertificateLink() != null && !dto.getCertificateLink().isEmpty();
        boolean hasDocuments = dto.getDocuments() != null && !isDocumentsEmpty(dto.getDocuments());;

        return hasLink || hasDocuments;
    }

    private boolean isDocumentsEmpty(DocumentsDTO doc) {
    return (doc.getFileUrl() == null || doc.getFileUrl().isEmpty())
        && (doc.getFileName() == null || doc.getFileName().isEmpty())
        && (doc.getFileType() == null || doc.getFileType().isEmpty());
}
}
