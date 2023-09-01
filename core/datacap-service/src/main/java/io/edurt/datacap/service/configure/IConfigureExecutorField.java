package io.edurt.datacap.service.configure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IConfigureExecutorField
{
    private String field;
    // The default is equal to the filed value, and the custom column name uses
    private String origin;
    private boolean required;
    // If the flag is true
    // it means that the field is extracted through user configuration, and the default data will be discarded
    private boolean override = false;
    // Is it an input parameter
    private boolean input = false;
    // Related component width
    private int width = 300;
    // Component type
    private FieldType type = FieldType.INPUT;
    private String tooltip;
    private Object value;
}
