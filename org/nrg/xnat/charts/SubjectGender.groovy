package org.nrg.xnat.charts

import org.nrg.xnat.BarGraph
import org.nrg.xnat.XnatChart
import org.nrg.xnat.DataPoint
import org.nrg.xnat.TexUtils
import org.nrg.xnat.enums.Gender
import org.nrg.xnat.pogo.Project

class SubjectGender extends XnatChart {

    @Override
    int chartId() {
        1
    }

    @Override
    String fileNameBase() {
        'subjectGender'
    }

    @Override
    String generateChart(Project project) {
        final List<DataPoint<Integer>> dataPoints = []
        Gender.values().each { gender ->
            final int count = project.getAllSubjects().findAll { it.gender == gender }.size()
            if (count > 0) dataPoints << new DataPoint<Integer>(color: color(gender), value: count, label: gender.capitalize())
        }
        new BarGraph<Integer>(values: dataPoints, title: "Subject gender distribution for project ${TexUtils.projectId(project)}").writeFullTeX()
    }

    String color(Gender gender) {
        switch (gender) {
            case Gender.MALE:
                return 'blue'
            case Gender.FEMALE:
                return 'pink'
            case Gender.UNKNOWN:
                return 'black'
        }
    }

}
