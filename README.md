# Jsoup cli


```
usage: [option]
 -h,--html <html>           Provide raw html
    --help
 -s,--selector <selector>   Jsoup HTML selector
 -u,--url <url>             Fetch html from url
```

```
docker run -it joakimfristedt/jsoup-cli -u https://google.se -s body
```

```
docker run -it joakimfristedt/jsoup-cli -h "<html><body><p>test</p></body></html>" -s "body p"
```

