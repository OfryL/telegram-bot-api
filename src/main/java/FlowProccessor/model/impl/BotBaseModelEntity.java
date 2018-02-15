package FlowProccessor.model.impl;

import FlowProccessor.model.IBotFlowModel;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BotBaseModelEntity implements BotApiObject, IBotFlowModel {

    public Object get(String fieldName){

        Optional<Method> getterMethodOptional = Stream.of(getClass().getMethods())
                .filter(method1 -> method1.getName().equalsIgnoreCase("get" + fieldName) ||
                                    method1.getName().equalsIgnoreCase("is" + fieldName))
                .findFirst();

        Object val = null;;

        try {
            val = getterMethodOptional
                    .orElseThrow(() -> new RuntimeException("No getter found for field with value: " + fieldName))
                    .invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return val;
    }

    public void set(String fieldName, Object value){

        Optional<Method> setterMethodOptional = Stream.of(getClass().getMethods())
                .filter(method1 -> method1.getName().equalsIgnoreCase("set" + fieldName))
                .findFirst();

        try {
             setterMethodOptional
                    .orElseThrow(() -> new RuntimeException("No setter found for field with value: " + fieldName))
                    .invoke(this, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
