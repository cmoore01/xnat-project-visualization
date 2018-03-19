package org.nrg.xnat

abstract class TexChart {
    double scale = 2.0
    String title

    String writeFullTeX() {
        produceTeX().replace('$scale$', String.valueOf(scale)).replace('$title$', title)
    }

    abstract String produceTeX()

}
