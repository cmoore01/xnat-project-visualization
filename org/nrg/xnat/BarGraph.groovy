package org.nrg.xnat

class BarGraph<X> {
    double scale
    String title
    List<DataPoint<X>> values

    String produceTeX() {
        new File('templates/barGraph.tex').text.replace('$scale$', String.valueOf(scale)).replace('$labels$', values.label.join(', ')).replace('$title$', title).replace('$plots$', readPlots()).replace('$x limits$', String.valueOf(xLimits()))
    }

    private String readPlots() {
        values.collect { dataPoint ->
            plot.replace('$color$', dataPoint.color).replace('$label$', dataPoint.label).replace('$value$', String.valueOf(dataPoint.value)).replace('$width$', dataPoint.width)
        }.join('\n')
    }

    private static final String plot = new File('templates/barGraphPlot.tex').text

    private double xLimits() {
        switch (values.size()) {
            case 2:
                return 0.5
            case 3:
                return 0.25
            default:
                return 0.1
        }
    }
}