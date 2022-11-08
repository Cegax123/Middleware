import socket

class Client:
    def __init__(self):
        self.client_socket = socket.socket()
        
    def connect_to_server(self, host, port, n_queue):
        self.n_queue = n_queue
        self.client_socket.connect((host, port))
        self.client_socket.send(("consumer "+str(n_queue)+"\r\n").encode())
    
    def run(self):
        while True:
            data = self.client_socket.recv(1024).decode()
            print("from queue " + str(self.n_queue) + ": " + str(data))
    
if __name__ == "__main__":
    client = Client()
    client.connect_to_server("localhost", 4444, 0)
    client.run()
