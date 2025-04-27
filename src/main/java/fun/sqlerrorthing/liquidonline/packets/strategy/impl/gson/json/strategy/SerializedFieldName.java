package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.strategy;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;

import java.lang.reflect.Field;

public class SerializedFieldName implements FieldNamingStrategy {
    private final FieldNamingStrategy baseStrategy = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

    @Override
    public String translateName(Field field) {
        var serializedName = field.getAnnotation(SerializedName.class);
        if (serializedName != null) {
            return serializedName.value();
        } else {
            return baseStrategy.translateName(field);
        }
    }
}
