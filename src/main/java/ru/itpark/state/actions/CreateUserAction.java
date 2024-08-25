package ru.itpark.state.actions;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.itpark.state.UserState;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreateUserAction implements Executor {
    @Override
    public void execute(UserState state, long userId) {
        final List<Message> userMessages = state.getMessages().get(userId);

        if (userMessages.size() < 3) return;

        var userDataToCreate = selectLastNMessages(userMessages, 3);


        log.info(userDataToCreate.toString());
//        userRepository.createUser(userDataToCreate.get(0), userDataToCreate.get(1), userDataToCreate.get(2));
    }

    private List<String> selectLastNMessages(List<Message> messages, int n) {
        var arrayList = new ArrayList<String>();

        for (int i = messages.size() - n; i < messages.size(); i++) {
            arrayList.add(messages.get(i).getText());
        }

        return arrayList;
    }
}
