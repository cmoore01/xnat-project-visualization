#!/usr/bin/env groovy
import org.nrg.xnat.XnatChart
import org.nrg.xnat.ChartRegistry
import org.nrg.xnat.interfaces.XnatInterface
import org.nrg.xnat.pogo.Project

@GrabResolver(name='NRG Release Repo', root='https://nrgxnat.jfrog.io/nrgxnat/libs-release')
@GrabResolver(name='NRG Snapshot Repo', root='https://nrgxnat.jfrog.io/nrgxnat/libs-snapshot')
@Grapes([
        @GrabExclude("org.codehaus.groovy:groovy-xml"),
        @GrabExclude("org.codehaus.groovy:groovy-json"),
        @Grab(group = 'org.nrg', module = 'grxnat', version = '1.10-SNAPSHOT')
])

final CliBuilder cli = new CliBuilder()

cli.with {
    h(longOpt: 'help', 'Usage Information', required: false)
    u(longOpt: 'user', 'Username for XNAT account', args: 1, required: false)
    p(longOpt: 'pass', 'Password for the user', args: 1, required: false)
    s(longOpt: 'url', 'XNAT URL', args: 1, required: true)
    j(longOpt: 'project', 'Project to chart.', args: 1, required: true)
    c(longOpt: 'charts', 'Comma-separated integer list of charts to generate.', args: 1, required: true)
    o(longOpt: 'output', 'Directory in which to place output', args: 1, required: false)
}

if ('-h' in args || '--help' in args) {
    cli.usage()
    return
}

final OptionAccessor params = cli.parse(args)
final String data = params.d as String
final String user = params.u as String
final String pass = params.p as String
final String url = params.s as String
final String projectString = params.j as String
final String chartsString = params.c as String
final String output = params.o ?: '.'

final String[] splitCharts = chartsString.split(',')
final List<XnatChart> requestedCharts = splitCharts.collect { ChartRegistry.byId(Integer.parseInt(it)) }
final XnatInterface xnatInterface = XnatInterface.authenticate(url, user, pass)
requestedCharts.any { it.requiresResources() } ? xnatInterface.enableResourceReading() : xnatInterface.disableResourceReading()
final Project project = xnatInterface.readProject(projectString)
requestedCharts.each { chart ->
    chart.build(project, output)
}