package de.spqrinfo.quotes.backend.security.beans.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String firstProperty;
    private String secondProperty;

    @Override
    public void initialize(final FieldsMatch constraintAnnotation) {
        this.firstProperty = constraintAnnotation.firstProperty();
        this.secondProperty = constraintAnnotation.secondProperty();
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        if (null == obj) {
            return true;
        }

        final PropertyDescriptor fstPropertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), this.firstProperty);
        final PropertyDescriptor sndPropertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), this.secondProperty);

        final Method fstReadMethod = fstPropertyDescriptor.getReadMethod();
        final Method sndReadMethod = sndPropertyDescriptor.getReadMethod();

        try {
            final Object fstValue = fstReadMethod.invoke(obj);
            final Object sndValue = sndReadMethod.invoke(obj);
            return fstValue.equals(sndValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
