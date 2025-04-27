package fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json.introspectors;

import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;

public class AnnotationIntrospector extends JacksonAnnotationIntrospector {
    @Override
    public PropertyName findNameForDeserialization(Annotated a) {
        var annotation = _findAnnotation(a, SerializedName.class);
        if (annotation != null) {
            return PropertyName.construct(annotation.value());
        } else {
            return super.findNameForDeserialization(a);
        }
    }

    @Override
    public PropertyName findNameForSerialization(Annotated a) {
        var annotation = _findAnnotation(a, SerializedName.class);
        if (annotation != null) {
            return PropertyName.construct(annotation.value());
        } else {
            return super.findNameForSerialization(a);
        }
    }
}
