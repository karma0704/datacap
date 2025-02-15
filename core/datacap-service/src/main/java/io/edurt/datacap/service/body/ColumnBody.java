package io.edurt.datacap.service.body;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.edurt.datacap.common.sql.configure.SqlType;
import io.edurt.datacap.sql.model.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class ColumnBody
{
    private SqlType type;
    private List<Column> columns;
}
