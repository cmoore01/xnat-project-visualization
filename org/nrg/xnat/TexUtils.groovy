package org.nrg.xnat

import org.nrg.xnat.pogo.Project

class TexUtils {

    static String projectId(Project project) {
        project.id.replace('_', '\\_')
    }

}
