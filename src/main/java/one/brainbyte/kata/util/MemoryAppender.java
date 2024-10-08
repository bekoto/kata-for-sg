package one.brainbyte.kata.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class MemoryAppender extends AppenderBase<ILoggingEvent> {
    private StringBuilder logOutput = new StringBuilder();

    @Override
    protected void append(ILoggingEvent event) {
        logOutput.append(event.getFormattedMessage()).append("\n");
    }

    public String getLogOutput() {
        return logOutput.toString();
    }
}
