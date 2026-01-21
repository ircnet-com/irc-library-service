package com.ircnet.library.service.user;

import java.util.Objects;

public record IpAddress(IpAddressFamily family, String address) {
    public IpAddress {
        Objects.requireNonNull(family, "family must not be null");
        address = Objects.requireNonNull(address, "address must not be null");
    }

    public static IpAddress ipv4(String address) {
        return new IpAddress(IpAddressFamily.IPV4, address);
    }

    public static IpAddress ipv6(String address) {
        return new IpAddress(IpAddressFamily.IPV6, address);
    }
}
