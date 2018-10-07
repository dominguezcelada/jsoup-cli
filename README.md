# Jsoup cli

```
docker run -it joakimfristedt/jsoup-cli --help

usage: [options]
 -a,--attribute <attribute>   Output attribute value instead of text.
                              E.g --attribute=href
 -h,--html <html>             Provide raw html
    --help
 -s,--selector <selector>     Jsoup HTML selector
 -t,--list-tags               List all children tags in selected query
 -u,--url <url>               Fetch html from url
```

```
docker run -it joakimfristedt/jsoup-cli -u https://google.se -s body
```

```
docker run -it joakimfristedt/jsoup-cli -h "<html><body><p>test</p></body></html>" -s "body p"
```

