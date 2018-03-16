package org.nrg.xnat

import org.nrg.xnat.charts.*

class ChartRegistry {

    public static final List<Chart> CHARTS = [
            new SubjectGender(),
            new SessionScanCountHistogram()
    ]

    static Chart byId(int id) {
        CHARTS.find { it.chartId() == id }
    }

}
