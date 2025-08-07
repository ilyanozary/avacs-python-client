# AVACS Chat Client Research Project

این پروژه یک پیاده‌سازی تحقیقاتی از کلاینت چت AVACS است که با هدف مطالعه و فهم پروتکل ارتباطی آن ساخته شده است.

## ویژگی‌ها

- پیاده‌سازی پروتکل اصلی AVACS در پایتون
- پشتیبانی از SOCKS5 proxy
- پشتیبانی از چند فرمت پیام مختلف
- تحلیل و پردازش پیام‌های باینری

## پیش‌نیازها

```bash
pip install -r requirements.txt
```

## نحوه استفاده

```python
client = AvacsClient()
client.connect()
```
