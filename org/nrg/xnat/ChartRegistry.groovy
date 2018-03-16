package org.nrg.xnat

import org.nrg.xnat.charts.SubjectGender

class ChartRegistry {

    public static final List<Chart> CHARTS = [
            new SubjectGender()
    ]

    static Chart byId(int id) {
        CHARTS.find { it.chartId() == id }
    }

}
