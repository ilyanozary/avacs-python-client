# AVACS Python Client

A Python implementation of the AVACS Chat protocol, providing both direct and SOCKS5 proxy connection support for the AVACS chat service.

## Features

- **Multiple Connection Methods**
  - Direct connection to AVACS servers
  - SOCKS5 proxy support (compatible with V2Ray)
  - Automatic fallback between multiple servers

- **Protocol Support**
  - Full implementation of AVACS binary protocol
  - Support for both text and binary message formats
  - Compatible with Java ME client protocol

- **Connection Features**
  - Automatic server selection
  - Multiple authentication methods
  - Reliable connection handling
  - Automatic reconnection

## Requirements

- Python 3.6+
- PySocks library for SOCKS5 proxy support

## Installation

1. Clone the repository:
```bash
git clone https://github.com/ilyanozary/avacs-python-client.git
cd avacs-python-client
```

2. Install dependencies:
```bash
pip install -r requirements.txt
```

## Usage

### Basic Usage

```python
from avacs_client import AvacsClient

client = AvacsClient()
client.username = "your_username"
client.password = "your_password"
client.connect()
```

### With Proxy

```python
client = AvacsClient()
client.use_proxy = True
client.proxy_ports = [1080]  # Your SOCKS5 proxy port
client.connect()
```

## Protocol Implementation

The client implements the following AVACS protocol commands:

- **Command Type 10**: Initial connection
- **Command Type 15**: Login authentication
- **Command Type 17**: Acknowledgment
- **Command Type 40**: Secondary connection
- **Command Type 100**: Feature check
- **Command Type 102**: Operation response
- **Command Type 110**: Data transfer
- **Command Type 500**: Error handling

## Project Structure

- `avacs_client.py` - Main client implementation
- `requirements.txt` - Project dependencies

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the maintainers.

## Acknowledgments

- AVACS Chat service for the protocol specification
- Original Java ME client developers for protocol reference
