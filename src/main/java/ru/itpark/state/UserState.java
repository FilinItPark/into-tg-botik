package ru.itpark.state;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.itpark.state.actions.CreateUserAction;
import ru.itpark.state.actions.Executor;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class UserState {
    private ConcurrentHashMap<Long, List<Message>> messages = new ConcurrentHashMap<>();
    private HashMap<String, Executor> actions = new HashMap<>();
    private static UserState instance = null;


    private UserState() {
        actions.put("createUser", (state, userId) -> {

        });
    }

    public void execute(String actionName, long userId) {
        actions.get(actionName).execute(this, userId);
    }

    public static UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }
}
