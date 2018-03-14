package FlowProccessor.config;

public class ProcessorConfig {

    private boolean debugMode;

    public boolean isDebugMode() {
        return debugMode;
    }

    private void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public static class ProcessorConfigBuilder {

        private boolean debugMode;

        public ProcessorConfigBuilder() {}

        public ProcessorConfig build() {

            ProcessorConfig config = new ProcessorConfig();
            config.setDebugMode(
                    debugMode
            );

            return config;
        }

        public ProcessorConfigBuilder debugMode(boolean debugMode) {
            this.debugMode = debugMode;
            return this;
        }
    }
}
