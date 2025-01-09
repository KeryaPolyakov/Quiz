package com.kirillpolyakov.util;

import com.kirillpolyakov.model.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {

    public static void checkPermission(long checkId, String ex) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        long id = userDetails.getId();
        if (checkId != id) {
            throw new IllegalArgumentException(ex);
        }
    }
}
