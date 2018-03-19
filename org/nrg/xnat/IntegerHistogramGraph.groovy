package org.nrg.xnat

class IntegerHistogramGraph extends TexChart {
    int bins = 0
    String xlabel
    List<Integer> data

    String produceTeX() {
        new File('templates/integerHistogram.tex').text.replace('$xlabel$', xlabel).replace('$bins$', String.valueOf(numBins())).replace('$data$', "            ${data.join('\\\\ ')}\\\\").replace('$max$', String.valueOf(data.max())).replace('$labelRotation$', String.valueOf(rotation())).replace('$font$', font())
    }

    private int numBins() {
        if (bins != 0) return bins
        switch (data.unique(false).size()) {
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

    private int rotation() {
        numBins() > 5 ? 45 : 0
    }

    private String font() {
        numBins() > 5 ? '\\tiny' : '\\normalsize'
    }

}