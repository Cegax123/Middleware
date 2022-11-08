from client import Client
    
if __name__ == "__main__":
    client = Client()
    client.connect_to_server("192.168.1.9", 4444, 3)
    client.run()

