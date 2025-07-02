package com.manish.jobportal.service.admin;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.manish.jobportal.dto.DialCodeDTO;
import com.manish.jobportal.dto.admin.management.CategoryDTO;
import com.manish.jobportal.models.DialCode;
import com.manish.jobportal.models.Role;
import com.manish.jobportal.models.User;
import com.manish.jobportal.models.admin.management.Category;
import com.manish.jobportal.repository.DialCodeRepository;
import com.manish.jobportal.repository.admin.management.CategoryRepository;
import com.manish.jobportal.utils.VerifyUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final CategoryRepository categoryRepository;
    private final DialCodeRepository dialCodeRepository;
    private final VerifyUser verifyUser;

    private boolean isAdmin(User user) {
        return user != null && user.getRole() == Role.ADMIN;
    }

    public ResponseEntity<?> addDialCode(HttpServletRequest request,
            @Valid @RequestBody DialCodeDTO dto) {
        User user = verifyUser.verify(request);
        if (!isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied. Admin role required."));
        }

        DialCode dialCode = DialCode.builder()
                .countryName(dto.getCountryName())
                .countryCode(dto.getCountryCode())
                .dialCode(dto.getDialCode())
                .build();

        dialCodeRepository.save(dialCode);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Dial Code added successfully."));
    }

    public ResponseEntity<?> removeDialCode(HttpServletRequest request,
            @Valid @RequestBody DialCodeDTO dto) {
        User user = verifyUser.verify(request);
        if (!isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied. Admin role required."));
        }

        boolean exists = dialCodeRepository.existsByCountryNameAndCountryCodeAndDialCode(
                dto.getCountryName(), dto.getCountryCode(), dto.getDialCode());

        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Dial Code with provided details does not exist."));
        }

        dialCodeRepository.deleteByCountryNameAndCountryCodeAndDialCode(
                dto.getCountryName(), dto.getCountryCode(), dto.getDialCode());

        return ResponseEntity.ok(Map.of("message", "Dial Code deleted successfully."));
    }

    public ResponseEntity<?> addCategory(HttpServletRequest request, CategoryDTO categoryDTO) {
        User user = verifyUser.verify(request);
        if (!isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied. Admin role required."));
        }
        Category category = Category.builder()
                .category(categoryDTO.getCategory())
                .build();

        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Category added successfully."));
    }
}
