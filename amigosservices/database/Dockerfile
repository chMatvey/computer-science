FROM postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_DB postgres
ENV POSTGRES_PASSWORD password
VOLUME /var/lib/postgresql/data
COPY init.sql /docker-entrypoint-initdb.d/