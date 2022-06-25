/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.sql.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author nacho
 */
public class FacturasFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getThreadID() + "::" + record.getSourceClassName() + "::"
                + record.getSourceMethodName() + "::"
                + new Date(record.getMillis()) + "::"
                + record.getMessage() + "\n";
    }
}
