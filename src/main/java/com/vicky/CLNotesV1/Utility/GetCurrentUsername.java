package com.vicky.CLNotesV1.Utility;

import org.springframework.security.core.context.SecurityContextHolder;

public class GetCurrentUsername {
    public static String get() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
