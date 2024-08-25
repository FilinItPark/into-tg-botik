package ru.itpark.state.actions;

import ru.itpark.state.UserState;

public interface Executor {
    void execute(UserState state, long userId);
}
