package org.nrg.xnat.charts

import org.nrg.xnat.XnatChart
import org.nrg.xnat.IntegerHistogramGraph
import org.nrg.xnat.TexUtils
import org.nrg.xnat.pogo.Project

class SessionScanCountHistogram extends XnatChart {

    @Override
    int chartId() {
        2
    }

    @Override
    String fileNameBase() {
        'scanCount'
    }

    @Override
    String generateChart(Project project) {
        final List<Integer> counts = project.subjects.collect { it.sessions }.flatten().collect { it.scans.size() }

        new IntegerHistogramGraph(title: "Scan count per session distribution for project ${TexUtils.projectId(project)}", xlabel: 'Number of scans in session', data: counts).writeFullTeX()
    }

}
