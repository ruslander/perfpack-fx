FROM centos:7


ENV RUBY_DIR /ruby
ENV RUBY_VERSION 2.5.0
ENV RUBY_INSTALL $RUBY_DIR/$RUBY_VERSION

RUN rpm -Uvh \
    https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
    yum update -y && \
    yum install -y make which wget tar git \
    gcc patch readline-devel zlib-devel      \
    libyaml-devel libffi-devel openssl-devel \
    gdbm-devel ncurses-devel libxml-devel bzip2

RUN cd /usr/src && \
    git clone https://github.com/sstephenson/ruby-build.git && \
    ./ruby-build/install.sh && \
    mkdir -p $RUBY_INSTALL && \
    /usr/local/bin/ruby-build $RUBY_VERSION $RUBY_INSTALL && \
    rm -rf /usr/src/ruby-build

ENV PATH $RUBY_INSTALL/bin:$PATH


# R
RUN yum -y install epel-release
RUN yum -y install R --nogpgcheck

# UI
# --------------------------------------------------------------------------------------------------------------
#
WORKDIR /app
ADD app/. /app
RUN gem install bundler -v '1.17.3'
RUN bundle install

USER nobody

ENTRYPOINT ["ruby", "app.rb"]
