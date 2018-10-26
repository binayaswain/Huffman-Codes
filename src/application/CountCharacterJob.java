package application;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CountCharacterJob implements Runnable {

    private final String character;

    private final Map<String, AtomicInteger> inputMap;

    public CountCharacterJob(String character, Map<String, AtomicInteger> inputMap) {
        this.character = character;
        this.inputMap = inputMap;
    }

    @Override
    public void run() {
        if (!character.matches("[A-Za-z0-9]")) {
            return;
        }

        if (inputMap.containsKey(character)) {
            inputMap.get(character).getAndIncrement();
            return;
        }

        AtomicInteger oldValue = inputMap.put(character, new AtomicInteger(1));

        if (oldValue != null) {
            inputMap.get(character).getAndAdd(oldValue.get());
        }
    }

}
