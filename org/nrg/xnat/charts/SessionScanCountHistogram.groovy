package org.nrg.xnat.charts

import org.nrg.xnat.Chart
import org.nrg.xnat.IntegerHistogramGraph
import org.nrg.xnat.TexUtils
import org.nrg.xnat.pogo.Project

class SessionScanCountHistogram extends Chart {

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

        new IntegerHistogramGraph(title: "Scan count per session distribution for project ${TexUtils.projectId(project)}", xlabel: 'Number of scans in session', data: counts, bins: numBins(counts)).produceTeX()
    }

    private int numBins(List<Integer> counts) {
        switch (counts.unique(false).size()) {
            case { it < 5 }:
                return 3
            case { it >= 5 && it < 12 }:
                return 5
            case { it >= 12 && it < 20 }:
                return 7
            case { it >= 20 && it < 30 }:
                return 10
            default:
                return 12
        }
    }

}
