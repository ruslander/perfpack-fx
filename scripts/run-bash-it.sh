docker build -t perflab-fx -f continer/Dockerfile .

docker run -it --entrypoint /bin/sh -v $PWD/app:/demo -w /demo -p 8080:8080 perflab-fx