package org.nrg.xnat

class IntegerHistogramGraph {
    int bins = 10
    String title
    String xlabel
    List<Integer> data

    String produceTeX() {
        new File('templates/integerHistogram.tex').text.replace('$xlabel$', xlabel).replace('$title$', title).replace('$bins$', String.valueOf(bins)).replace('$data$', data.join('\\\\ ') + '\\\\').replace('$max$', String.valueOf(data.max()))
    }

}