package com.vicky.CLNotesV1.Entity;

import java.util.Set;

public enum Roles {
    ADMIN(Set.of(Permissions.USER_READ, Permissions.USER_WRITE, Permissions.USER_DELETE,
            Permissions.NOTE_DELETE, Permissions.NOTE_WRITE, Permissions.NOTE_READ,
            Permissions.TAG_READ, Permissions.TAG_WRITE, Permissions.TAG_DELETE)),
    USER(Set.of(Permissions.USER_READ,
            Permissions.NOTE_DELETE, Permissions.NOTE_WRITE, Permissions.NOTE_READ,
            Permissions.TAG_READ, Permissions.TAG_WRITE, Permissions.TAG_DELETE));

    private final Set<Permissions> permissionsSet;
    Roles(Set<Permissions> permissionsSet) {
        this.permissionsSet = permissionsSet;
    }

    public Set<Permissions> getPermissionsSet() {
        return permissionsSet;
    }
}
