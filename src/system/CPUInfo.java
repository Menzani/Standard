package eu.menzani.system;

import eu.menzani.data.ParseException;
import eu.menzani.io.ProcessLauncher;

public class CPUInfo extends PlatformFamilyDependant {
    static final CPUInfo value = new CPUInfo();

    private int numberOfCores;
    private int numberOfHardwareThreads;
    private int numberOfHardwareThreadsPerCore;

    private CPUInfo() {
    }

    @Override
    protected void init() {
        numberOfCores = -1;
        numberOfHardwareThreads = -1;
    }

    @Override
    protected void onWindows() {
        ProcessLauncher launcher = new ProcessLauncher("wmic");
        launcher.addArguments("CPU", "Get", "NumberOfCores,NumberOfLogicalProcessors", "/Format:List");
        for (String line : launcher.start().readOutputLines()) {
            if (line.isEmpty()) continue;
            int indexOfEquals = line.indexOf('=');
            String key = line.substring(0, indexOfEquals);
            int value = Integer.parseInt(line.substring(indexOfEquals + 1));
            switch (key) {
                case "NumberOfCores":
                    numberOfCores = value;
                    break;
                case "NumberOfLogicalProcessors":
                    numberOfHardwareThreads = value;
                    break;
                default:
                    throw new ParseException();
            }
        }
        if (numberOfCores == -1) throw new ParseException();
        if (numberOfHardwareThreads == -1) throw new ParseException();
    }

    @Override
    protected void complete() {
        numberOfHardwareThreadsPerCore = numberOfHardwareThreads / numberOfCores;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    public int getNumberOfHardwareThreads() {
        return numberOfHardwareThreads;
    }

    public int getNumberOfHardwareThreadsPerCore() {
        return numberOfHardwareThreadsPerCore;
    }
}
