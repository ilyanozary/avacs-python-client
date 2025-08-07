import socket
import ssl
import time
import os
import socks  # برای استفاده از SOCKS proxy

class AvacsClient:
    def __init__(self):
        self.proxy_ports = [1080, 1087]  # پورت‌های معمول V2Ray
        self.use_proxy = True
        
        self.servers = [
            ("chat.avacschat.com", 9001),  # سرور چت (اول این را تست می‌کنیم)
            ("socket.avacschat.com", 9011),
            ("socket.avacs.net", 9001),
            ("chat.avacschat.com", 80),  # وب سرور را آخر تست می‌کنیم
        ]
        
        # تنظیمات اضافی
        self.buffer = b""  # برای نگهداری داده‌های ناقص
        self.connected = False
        self.username = "TestUser"
        self.password = "test123"
        self.socket = None
        
    def connect(self):
        for host, port in self.servers:
            # اول با پروکسی تلاش می‌کنیم
            if self.use_proxy:
                for proxy_port in self.proxy_ports:
                    try:
                        print(f"\nTrying to connect to {host}:{port} via SOCKS5 proxy on port {proxy_port}...")
                        # تنظیم پروکسی
                        socks.set_default_proxy(socks.SOCKS5, "127.0.0.1", proxy_port)
                        socket.socket = socks.socksocket
                        
                        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                        self.socket.settimeout(10)
                        
                        if self.try_connection(host, port):
                            return True
                    except Exception as e:
                        print(f"Proxy connection failed on port {proxy_port}: {e}")
                        continue
            
            # اگر پروکسی کار نکرد، بدون پروکسی تلاش می‌کنیم
            try:
                print(f"\nTrying direct connection to {host}:{port}...")
                socket.socket = socks.socksocket
                socks.set_default_proxy()  # حذف تنظیمات پروکسی
                
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
                
                # پیام‌های handshake با فرمت‌های مشاهده شده در کد جاوا
                handshake_messages = [
                    # Command Type 15: Login attempt
                    bytes([15]) + len(f"1|{self.username}|{self.password}").to_bytes(1, 'big') + 
                        f"1|{self.username}|{self.password}".encode(),
                    
                    # Command Type 10: Initial connect
                    bytes([10]) + len("CONNECT").to_bytes(1, 'big') + b"CONNECT",
                    
                    # Command Type 40: Secondary connect
                    bytes([40]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                        f"1|{self.username}".encode(),
                    
                    # Command Type 100: Feature check
                    bytes([100]),
                    
                    # Command Type 110: Data transfer
                    bytes([110]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                        f"1|{self.username}".encode(),
                ]
                
                print("\nStarting handshake attempts...")
                for i, msg in enumerate(handshake_messages, 1):
                    print(f"\nAttempt {i}/{len(handshake_messages)}")
                    print(f"Sending: {msg.hex()}")
                    self.socket.send(msg)
                    
                    # چک کردن پاسخ با timeout کوتاه‌تر
                    self.socket.settimeout(3)  # timeout کوتاه‌تر برای هر تلاش
                    try:
                        response = self.socket.recv(1024)
                        print(f"Response hex: {response.hex()}")
                        print(f"Response text: {response.decode('utf-8', errors='ignore')}")
                        if response:  # اگر پاسخی دریافت کردیم
                            print("Got response! Analyzing...")
                            self.analyze_response(response)
                            if self.connected:  # اگر پاسخ معتبر بود
                                break
                    except socket.timeout:
                        print("No response (timeout)")
                    except Exception as e:
                        print(f"Error during handshake: {e}")
                        
                self.socket.settimeout(10)  # برگرداندن timeout به حالت عادی
                
                for msg in handshake_messages:
                    print(f"\nTrying handshake with: {msg}")
                    self.socket.send(msg)
                    
                    # منتظر پاسخ می‌مانیم
                    try:
                        response = self.socket.recv(1024)
                        print(f"Got response: {response}")
                        if response:  # اگر پاسخی دریافت کردیم
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

        # اگر هیچ اتصالی موفق نبود
        print("\nCould not connect to any server!")
        return False
        
    def try_connection(self, host, port):
        """تلاش برای اتصال و handshake با یک سرور"""
        try:
            try:
                ip = socket.gethostbyname(host)
                print(f"Resolved {host} to {ip}")
            except socket.gaierror:
                print(f"Could not resolve hostname {host}")
                return False
                
            self.socket.connect((host, port))
            print(f"Connected to {host}:{port}!")
            
            # پیام‌های handshake با فرمت‌های مشاهده شده در کد جاوا
            handshake_messages = [
                # Command Type 10: Initial connect (اول این رو تست می‌کنیم)
                bytes([10]) + len("CONNECT").to_bytes(1, 'big') + b"CONNECT",
                
                # Command Type 15: Login attempt
                bytes([15]) + len(f"1|{self.username}|{self.password}").to_bytes(1, 'big') + 
                    f"1|{self.username}|{self.password}".encode(),
                
                # Command Type 40: Secondary connect
                bytes([40]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                    f"1|{self.username}".encode(),
                
                # Command Type 100: Feature check
                bytes([100]),
                
                # Command Type 110: Data transfer
                bytes([110]) + len(f"1|{self.username}").to_bytes(1, 'big') + 
                    f"1|{self.username}".encode(),
            ]
            
            print("\nStarting handshake attempts...")
            for i, msg in enumerate(handshake_messages, 1):
                print(f"\nAttempt {i}/{len(handshake_messages)}")
                print(f"Sending: {msg.hex()}")
                self.socket.send(msg)
                
                # منتظر پاسخ می‌مانیم
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
                print(f"Received: {message.encode('utf-8')}")  # نمایش باینری پیام
                
                self.process_message(message)
                
            except socket.timeout:
                print("No data received (timeout)")
            except Exception as e:
                print(f"Error receiving message: {e}")
                break
    
    def analyze_response(self, response):
        """تحلیل پاسخ سرور بر اساس پروتکل AVACS"""
        try:
            hex_response = response.hex()
            print(f"Analyzing response (hex): {hex_response}")
            
            if len(response) < 1:
                print("Response too short")
                return
                
            command_type = response[0]
            print(f"Command type: {command_type}")
            
            # پردازش پاسخ بر اساس نوع کامند
            if command_type == 10:  # پاسخ اولیه
                print("Initial connect response")
                self.connected = True
            elif command_type == 15:  # پاسخ لاگین
                print("Login response")
                if len(response) > 1:
                    try:
                        result = response[1:].decode('utf-8', errors='ignore')
                        print(f"Login result: {result}")
                        if "1" in result:  # موفق
                            self.connected = True
                    except:
                        print("Could not decode login response")
            elif command_type == 17:  # تایید
                print("Acknowledgment")
                self.connected = True
            elif command_type == 40:  # پاسخ اتصال ثانویه
                print("Secondary connect response")
                self.connected = True
            elif command_type == 100:  # پاسخ چک ویژگی‌ها
                print("Feature check response")
            elif command_type == 102:  # پاسخ عملیات
                print("Operation response")
            elif command_type == 110:  # پاسخ انتقال داده
                print("Data transfer response")
            elif command_type == 500:  # خطا
                print("Error response")
            else:
                print(f"Unknown command type: {command_type}")
                
            # تلاش برای تفسیر داده‌ها
            if len(response) > 1:
                try:
                    data = response[1:].decode('utf-8', errors='ignore')
                    print(f"Response data: {data}")
                except:
                    print("Binary data received")
            
        except Exception as e:
            print(f"Error analyzing response: {e}")
    
    def process_message(self, message):
        """پردازش پیام‌های دریافتی پس از اتصال"""
        if isinstance(message, bytes):
            print(f"\nProcessing binary message: {message.hex()}")
            try:
                text = message.decode('utf-8', errors='ignore')
                print(f"As text: {text}")
            except:
                print("Could not decode as text")
            return
            
        print(f"\nProcessing text message: {message}")
        
        # تشخیص نوع پیام
        if message.startswith('\x01'):
            print("Java binary protocol message")
            message = message[1:]  # حذف byte اول
        elif message.startswith('\x00'):
            print("Null-terminated message")
            message = message.strip('\x00')
        
        # پردازش محتوای پیام
        parts = message.split('\t')
        if len(parts) > 1:
            command = parts[0].upper()
            print(f"Command: {command}")
            print(f"Parameters: {parts[1:]}")
    
    def send_message(self, message):
        try:
            if isinstance(message, str):
                # تست فرمت‌های مختلف ارسال
                formats = [
                    message.encode('utf-8'),
                    b'\x00' + message.encode('utf-8') + b'\x00',
                    b'\xFF' + message.encode('utf-8') + b'\xFF',
                    len(message).to_bytes(2, 'big') + message.encode('utf-8')
                ]
                
                for fmt in formats:
                    print(f"\nTrying to send in format: {fmt.hex()}")
                    self.socket.send(fmt)
                    time.sleep(0.5)  # کمی صبر می‌کنیم
            else:
                self.socket.send(message)
        except Exception as e:
            print(f"Error sending message: {e}")

if __name__ == "__main__":
    client = AvacsClient()
    client.connect()
