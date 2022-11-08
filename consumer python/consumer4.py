from client import Client
    
if __name__ == "__main__":
    client = Client()
    client.connect_to_server("localhost", 4444, 3)
    client.run()

