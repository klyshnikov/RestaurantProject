package ru.hse.restaurant.project.command.commands;

public interface Command {
    public void Execute() throws InterruptedException;
}
