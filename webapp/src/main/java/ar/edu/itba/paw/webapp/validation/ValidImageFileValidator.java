package ar.edu.itba.paw.webapp.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {
   @Override
   public boolean isValid( MultipartFile file, ConstraintValidatorContext context) {
       return file == null || file.isEmpty() || (file.getContentType().contains("image/jpg") || file.getContentType().contains("image/jpeg") || file.getContentType().contains("image/png"));
   }

}
