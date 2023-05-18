package com.university.alsamixer.amixer_utils;

import org.springframework.stereotype.Component;

import static java.lang.System.out;
import java.io.*;
import java.util.concurrent.TimeUnit;

@Component
public class PlatformHelper {
    public String executeAMixerCommand(String command) {
        out.println("Executing the following command:\n" + command);

        // assuming the OS is always Linux
        String[] LinuxCommand = new String[] { "sh", "-c", command };
        String commandOutput;

        try {
            // executing the command on an OS process
            Process process =  Runtime.getRuntime().exec(LinuxCommand);
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            commandOutput = getAMixerCommandOutput(inputStream);

            // waiting for 1 minute for the process to finish
            boolean isFinished = process.waitFor(60, TimeUnit.SECONDS);
            outputStream.flush();
            outputStream.close();

            if(!isFinished) {
                // killing the process if it's not completed within 1 minute
                process.destroyForcibly();
            }
        }
        catch(InterruptedException | IOException e) {
            commandOutput = "Command Could Not Be Executed";
            e.printStackTrace();
        }

        return commandOutput;
    }

    private String getAMixerCommandOutput(InputStream inputStream) throws IOException {
        String commandOutput = "";
        out.println("The command Output:");

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                out.println(line);
                commandOutput += line;
                commandOutput += "\n";
            }
        }
        return commandOutput;
    }
}
