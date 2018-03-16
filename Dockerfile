FROM openjdk:8
MAINTAINER Charlie Moore <moore.c@wustl.edu>
ENV GROOVY_VERSION 2.4.7
ENV GROOVY_HOME /usr/local/groovy
ENV PATH $PATH:$GROOVY_HOME/bin

RUN apt-get update

RUN apt-get install -qy texlive-full
RUN apt-get install -qy gnuplot

RUN wget -O groovy.zip "https://dl.bintray.com/groovy/maven/apache-groovy-binary-${GROOVY_VERSION}.zip" && \
    unzip groovy.zip && \
    rm groovy.zip && \
    mv "groovy-${GROOVY_VERSION}" $GROOVY_HOME

COPY . /home/
RUN cd /home && groovy -Dgroovy.grape.report.downloads=true VisualizeXnat.groovy -h

LABEL org.nrg.commands="[{\"inputs\": [{\"user-settable\": false, \"required\": \"true\", \"type\": \"string\", \"name\": \"id\", \"description\": \"Project ID extracted from wrapper inputs\"}, {\"required\": true, \"user-settable\": true, \"type\": \"string\", \"name\": \"charts\", \"description\": \"Comma-separated list of integers, representing the desired charts to generate.\"}], \"working-directory\": \"/home\", \"name\": \"xnat-project-visualization\", \"command-line\": \"groovy VisualizeXnat.groovy -u \$XNAT_USER -p \$XNAT_PASS -s \$XNAT_HOST -j #id# -c #charts# -o /output\", \"outputs\": [{\"mount\": \"tex-out\", \"required\": \"true\", \"name\": \"tex\", \"description\": \"The created TeX source files and PDFs\"}], \"image\": \"greppy/xnat-project-visualization\", \"override-entrypoint\": true, \"version\": \"1.0\", \"schema-version\": \"1.0\", \"xnat\": [{\"derived-inputs\": [{\"provides-value-for-command-input\": \"id\", \"name\": \"project-id\", \"derived-from-xnat-object-property\": \"id\", \"required\": true, \"derived-from-wrapper-input\": \"project\", \"description\": \"ID derived from project.\"}], \"contexts\": [\"xnat:projectData\"], \"description\": \"Produces various visuals representing different components of an XNAT study (project).\", \"output-handlers\": [{\"accepts-command-output\": \"tex\", \"label\": \"TeX\", \"type\": \"Resource\", \"name\": \"tex-resource\", \"as-a-child-of-wrapper-input\": \"project\"}], \"external-inputs\": [{\"load-children\": false, \"required\": true, \"type\": \"Project\", \"name\": \"project\", \"description\": \"The project to build stats from.\"}], \"name\": \"xnat-project-visualization\"}], \"mounts\": [{\"writable\": \"true\", \"path\": \"/output\", \"name\": \"tex-out\"}], \"type\": \"docker\", \"description\": \"Produces various visuals representing different components of an XNAT study (project).\"}]"
