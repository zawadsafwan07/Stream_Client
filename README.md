# Stream_Client
In this assignment, you will implement a program called StreamClient. You have to write
code to establish a TCP connection with a remote server, called the Stream Server, read the content of a given file from the local file system, send it to the server for processing, receive the
processed file content and save it to a new file in the local file system. The server provides the
following services to its clients:
• ECHO: The server sends back the same data it receives from the client without any modification.
• ZIP: The server receives data from the client, compresses it using the GZIP format, and
sends it back to the client.
• UNZIP: The server receives GZIP compressed data from the client, decompresses it, and
sends it back to the client.
