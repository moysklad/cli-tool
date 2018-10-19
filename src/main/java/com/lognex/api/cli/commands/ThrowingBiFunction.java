package com.lognex.api.cli.commands;

@FunctionalInterface
public interface ThrowingBiFunction<E1, E2, R, T extends Throwable> {
    R accept(E1 e1, E2 e2) throws T;
}
