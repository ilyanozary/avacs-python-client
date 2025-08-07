import socket
import ssl
import time
import os
import socks

class AvacsClient:
    """
    A Python implementation of the AVACS chat client protocol.
    This client supports both direct connections and SOCKS5 proxy,
    implementing the binary protocol used by the Java ME client.
    """
    def __init__(self):
        # Common proxy ports for V2Ray or other tools
        self.proxy_ports = [1080, 1087]
        self.use_proxy = True

        # Server list ordered by priority
        self.servers = [
            ("chat.avacschat.com", 9001),  # Primary chat server
            ("socket.avacschat.com", 9011), # Backup chat server
            ("socket.avacs.net", 9001),     # Alternative domain
            ("chat.avacschat.com", 80),     # HTTP fallback
        ]

        # Connection state and buffers
        self.buffer = b""        # Buffer for incomplete data
        self.connected = False   # Connection status
        self.username = "TestUser"
        self.password = "test123"
        self.socket = None       # Active socket connection

    def connect(self):
        for host, port in self.servers:
            if self.use_proxy:
                for proxy_port in self.proxy_ports:
                    try:
                        print(f"\nTrying to connect to {host}:{port} via SOCKS5 proxy on port {proxy_port}...")
                        socks.set_default_proxy(socks.SOCKS5, "127.0.0.1", proxy_port)
                        socket.socket = socks.socksocket

                        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                        self.socket.settimeout(10)

                        if self.try_connection(host, port):
                            return True
                    except Exception as e:
                        print(f"Proxy connection failed on port {proxy_port}: {e}")
                        continue

            try:
                print(f"\nTrying direct connection to {host}:{port}...")
                socket.socket = socks.socksocket
                socks.set_default_proxy()  # Remove proxy settings

                self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                self.socket.settimeout(10)

                try:
                    ip = socket.gethostbyname(host)
                    print(f"Resolved {host} to {ip}")
                except socket.gaierror:
                    print(f"Could not resolve hostname {host}")
                    continue

                self.socket.connect((host, port))
                print(f"Connected to {host}:{port}!")

                # Handshake messages to be sent after connection
                handshake_messages = [
                    bytes([15]) + len(f"1|{self.username}|{self.password}").to_bytes(1, 'big') + 
                        f"1|{self.username}|{self.password}".encode(),

                    bytes([10]) + len("CONNECT").to_bytes(1, 'big') + b"CONNECT",

                    bytes([40]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                        f"1|{self.username}".encode(),

                    bytes([100]),

                    bytes([110]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                        f"1|{self.username}".encode(),
                ]

                print("\nStarting handshake attempts...")
                for i, msg in enumerate(handshake_messages, 1):
                    print(f"\nAttempt {i}/{len(handshake_messages)}")
                    print(f"Sending: {msg.hex()}")
                    self.socket.send(msg)

                    self.socket.settimeout(3)  # Short timeout for each attempt
                    try:
                        response = self.socket.recv(1024)
                        print(f"Response hex: {response.hex()}")
                        print(f"Response text: {response.decode('utf-8', errors='ignore')}")
                        if response:
                            print("Got response! Analyzing...")
                            self.analyze_response(response)
                            if self.connected:
                                break
                    except socket.timeout:
                        print("No response (timeout)")
                    except Exception as e:
                        print(f"Error during handshake: {e}")

                self.socket.settimeout(10)  # Restore normal timeout

                for msg in handshake_messages:
                    print(f"\nTrying handshake with: {msg}")
                    self.socket.send(msg)

                    try:
                        response = self.socket.recv(1024)
                        print(f"Got response: {response}")
                        if response:
                            break
                    except socket.timeout:
                        print("No response to this handshake")

                self.receive_messages()
                return True

            except socket.timeout:
                print(f"Connection to {host}:{port} timed out")
            except ConnectionRefusedError:
                print(f"Connection to {host}:{port} was refused")
            except Exception as e:
                print(f"Error connecting to {host}:{port}: {e}")

            if self.socket:
                self.socket.close()
                self.socket = None

        print("\nCould not connect to any server!")
        return False

    def try_connection(self, host, port):
        try:
            try:
                ip = socket.gethostbyname(host)
                print(f"Resolved {host} to {ip}")
            except socket.gaierror:
                print(f"Could not resolve hostname {host}")
                return False

            self.socket.connect((host, port))
            print(f"Connected to {host}:{port}!")

            handshake_messages = [
                bytes([10]) + len("CONNECT").to_bytes(1, 'big') + b"CONNECT",

                bytes([15]) + len(f"1|{self.username}|{self.password}").to_bytes(1, 'big') + 
                    f"1|{self.username}|{self.password}".encode(),

                bytes([40]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                    f"1|{self.username}".encode(),

                bytes([100]),

                bytes([110]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                    f"1|{self.username}".encode(),
            ]

            print("\nStarting handshake attempts...")
            for i, msg in enumerate(handshake_messages, 1):
                print(f"\nAttempt {i}/{len(handshake_messages)}")
                print(f"Sending: {msg.hex()}")
                self.socket.send(msg)

                self.socket.settimeout(3)
                try:
                    response = self.socket.recv(1024)
                    print(f"Response hex: {response.hex()}")
                    print(f"Response text: {response.decode('utf-8', errors='ignore')}")
                    if response:
                        print("Got response! Analyzing...")
                        self.analyze_response(response)
                        if self.connected:
                            self.socket.settimeout(10)
                            self.receive_messages()
                            return True
                except socket.timeout:
                    print("No response (timeout)")
                except Exception as e:
                    print(f"Error during handshake: {e}")

            return False

        except socket.timeout:
            print(f"Connection to {host}:{port} timed out")
        except ConnectionRefusedError:
            print(f"Connection to {host}:{port} was refused")
        except Exception as e:
            print(f"Error connecting to {host}:{port}: {e}")

        if self.socket:
            self.socket.close()
            self.socket = None

        return False

    def receive_messages(self):
        print("\nWaiting for messages...")
        while True:
            try:
                data = self.socket.recv(1024)
                if not data:
                    print("Connection closed by server")
                    break

                message = data.decode('utf-8', errors='ignore')
                print(f"Received: {message.encode('utf-8')}")  # Show binary message

                self.process_message(message)

            except socket.timeout:
                print("No data received (timeout)")
            except Exception as e:
                print(f"Error receiving message: {e}")
                break

    def analyze_response(self, response):
        """
        Analyze server responses based on AVACS protocol commands.
        Set connection state if expected command is received.

        Command Types:
            10: Initial connection response
            15: Login response
            17: Acknowledgment
            40: Secondary connection
            100: Feature check
            102: Operation response
            110: Data transfer
            500: Error response
        """
        try:
            hex_response = response.hex()
            print(f"Analyzing response (hex): {hex_response}")

            if len(response) < 1:
                print("Response too short")
                return

            command_type = response[0]
            print(f"Command type: {command_type}")

            if command_type == 10:
                print("Initial connect response")
                self.connected = True
            elif command_type == 15:
                print("Login response")
                if len(response) > 1:
                    try:
                        result = response[1:].decode('utf-8', errors='ignore')
                        print(f"Login result: {result}")
                        if "1" in result:
                            self.connected = True
                    except:
                        print("Could not decode login response")
            elif command_type == 17:
                print("Acknowledgment")
                self.connected = True
            elif command_type == 40:
                print("Secondary connect response")
                self.connected = True
            elif command_type == 100:
                print("Feature check response")
            elif command_type == 102:
                print("Operation response")
            elif command_type == 110:
                print("Data transfer response")
            elif command_type == 500:
                print("Error response")
            else:
                print(f"Unknown command type: {command_type}")

            if len(response) > 1:
                try:
                    data = response[1:].decode('utf-8', errors='ignore')
                    print(f"Response data: {data}")
                except:
                    print("Binary data received")

        except Exception as e:
            print(f"Error analyzing response: {e}")

    def process_message(self, message):
        """
        Process a message received from the server.
        Handle different message formats.

        Message Formats:
            - Binary with 0x01 prefix
            - Null-terminated strings
            - Tab-separated command format
        """
        if isinstance(message, bytes):
            print(f"\nProcessing binary message: {message.hex()}")
            try:
                text = message.decode('utf-8', errors='ignore')
                print(f"As text: {text}")
            except:
                print("Could not decode as text")
            return

        print(f"\nProcessing text message: {message}")

        if message.startswith('\x01'):
            print("Java binary protocol message")
            message = message[1:]
        elif message.startswith('\x00'):
            print("Null-terminated message")
            message = message.strip('\x00')

        parts = message.split('\t')
        if len(parts) > 1:
            command = parts[0].upper()
            print(f"Command: {command}")
            print(f"Parameters: {parts[1:]}")

    def send_message(self, message):
        try:
            if isinstance(message, str):
                formats = [
                    message.encode('utf-8'),
                    b'\x00' + message.encode('utf-8') + b'\x00',
                    b'\xFF' + message.encode('utf-8') + b'\xFF',
                    len(message).to_bytes(2, 'big') + message.encode('utf-8')
                ]

                for fmt in formats:
                    print(f"\nTrying to send in format: {fmt.hex()}")
                    self.socket.send(fmt)
                    time.sleep(0.5)
            else:
                self.socket.send(message)
        except Exception as e:
            print(f"Error sending message: {e}")

if __name__ == "__main__":
    client = AvacsClient()
    client.connect()
