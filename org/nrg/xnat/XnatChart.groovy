package org.nrg.xnat

import com.google.common.io.Files
import org.nrg.xnat.pogo.Project

import java.nio.file.Paths

abstract class XnatChart {

    abstract int chartId()

    abstract String fileNameBase()

    abstract String generateChart(Project project)

    boolean requiresResources() {
        false
    }

    void build(Project project, String outputDir) {
        final String chart = generateChart(project)
        final File tex = new File("${fileNameBase()}.tex")
        tex.createNewFile()
        tex << new File('templates/document.tex').text.replace('%content%', chart)

        final StringBuilder stdOut = new StringBuilder()
        final StringBuilder stdErr = new StringBuilder()
        final Process process = "pdflatex -halt-on-error ${tex.getName()}".execute()
        process.consumeProcessOutput(stdOut, stdErr)
        process.waitForOrKill(10000)
        if (outputDir != '.') {
            ['tex', 'pdf'].each { extension ->
                final File file = new File("${fileNameBase()}.${extension}")
                Files.copy(file, Paths.get(outputDir, file.getName()).toFile())
            }
        }
    }

}
