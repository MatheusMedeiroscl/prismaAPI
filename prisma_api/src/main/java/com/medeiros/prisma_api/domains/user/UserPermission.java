package com.medeiros.prisma_api.domains.user;

public enum UserPermission {
    ADMIN("admin"),
    SELLER("seller");

    private final String permission;

    UserPermission(String permission){this.permission = permission;}
    public String getPermission(){return permission;}
}
