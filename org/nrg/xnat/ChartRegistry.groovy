package org.nrg.xnat

import org.nrg.xnat.charts.*

class ChartRegistry {

    public static final List<XnatChart> CHARTS = [
            new SubjectGender(),
            new SessionScanCountHistogram(),
            new SubjectAge()
    ]

    static XnatChart byId(int id) {
        CHARTS.find { it.chartId() == id }
    }

}
