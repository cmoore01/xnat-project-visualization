package org.nrg.xnat.charts

import org.nrg.xnat.IntegerHistogramGraph
import org.nrg.xnat.TexUtils
import org.nrg.xnat.XnatChart
import org.nrg.xnat.pogo.Project

class SubjectAge extends XnatChart {

    @Override
    int chartId() {
        3
    }

    @Override
    String fileNameBase() {
        'ageHistogram'
    }

    @Override
    String generateChart(Project project) {
        final List<Integer> counts = project.allSubjects.age

        new IntegerHistogramGraph(title: "Subject age distribution for project ${TexUtils.projectId(project)}", xlabel: 'Subject Age', data: counts).writeFullTeX()
    }

}
